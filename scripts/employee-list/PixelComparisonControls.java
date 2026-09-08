package com.vaadinbench.verifier;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.*;
import javax.imageio.ImageIO;

/** Dependency-free controls for the actual comparator used by the browser verifier. */
public class PixelComparisonControls {
    static int checks;
    static void check(boolean value, String reason) {
        checks++;
        if (!value) throw new AssertionError(reason);
    }
    static BufferedImage solid(int width,int height,int rgb) {
        BufferedImage im=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g=im.createGraphics();g.setColor(new Color(rgb));g.fillRect(0,0,width,height);g.dispose();return im;
    }
    static BufferedImage copy(BufferedImage im) {
        BufferedImage result=solid(im.getWidth(),im.getHeight(),0);
        result.getGraphics().drawImage(im,0,0,null);return result;
    }
    static void rejects(Runnable action,String reason) {
        try {action.run();throw new AssertionError(reason);} catch(IllegalArgumentException expected){checks++;}
    }
    static boolean profilePass(BufferedImage reference,BufferedImage actual,boolean open,PixelComparison.Profile p) {
        return PixelComparison.regions(open).stream().allMatch(region->PixelComparison.compare(reference,actual,region,p.channelTolerance())
                .passes(region.name().equals("whole")?p.whole():p.region(),p.edge()));
    }
    public static void main(String[] args) throws Exception {
        var full=new PixelComparison.Region("whole",0,0,100,100);
        BufferedImage a=solid(100,100,0xffffff),b=copy(a);
        check(PixelComparison.compare(a,b,full,0).passes(1,1),"Identical opaque RGB passes strict");
        b.setRGB(0,0,0xfffffe);
        check(!PixelComparison.compare(a,b,full,0).passes(1,1),"One channel at one pixel fails strict");
        check(PixelComparison.compare(a,b,full,8).passes(1,1),"Lenient tolerance includes small channel noise");
        b.setRGB(0,0,0xfffff7);
        check(PixelComparison.compare(a,b,full,8).passes(1,1),"Eight is inclusive");
        b.setRGB(0,0,0xfffff6);
        check(!PixelComparison.compare(a,b,full,8).passes(1,1),"Nine is a mismatch");
        b=copy(a);
        for(int n=0;n<500;n++)b.setRGB(n%100,n/100,0xf6f6f6);
        check(PixelComparison.compare(a,b,full,8).passes(.95,.90),"Exactly 95% passes");
        b.setRGB(0,5,0xf6f6f6);
        check(!PixelComparison.compare(a,b,full,8).passes(.95,.90),"94.99% fails; no rounded score");
        var wrong=solid(99,100,0xffffff);
        rejects(()->PixelComparison.compare(a,wrong,full,0),"Dimensions must match");
        rejects(()->PixelComparison.compare(a,a,new PixelComparison.Region("bad",99,0,2,2),0),"Out-of-bounds region");
        var transparent=new BufferedImage(100,100,BufferedImage.TYPE_INT_ARGB);
        rejects(()->PixelComparison.compare(a,transparent,full,0),"Transparent reference/capture cannot pass");
        BufferedImage text=copy(a);
        Graphics2D g=text.createGraphics();g.setColor(Color.BLACK);g.fillRect(10,10,30,2);g.dispose();
        var textResult=PixelComparison.compare(text,a,full,8);
        check(textResult.agreement()>.99,"Small removed text could hide in whole-image average");
        check(!textResult.passes(.95,.90),"Union edge check rejects removed text");
        var shifted=copy(a);g=shifted.createGraphics();g.setColor(Color.BLACK);g.fillRect(11,10,30,2);g.dispose();
        check(!PixelComparison.compare(text,shifted,full,0).passes(1,1),"Shift is not auto-aligned");
        for(boolean open:new boolean[]{false,true}) {
            String name=open?"employee-list":"employee-list-plain";
            BufferedImage reference=ImageIO.read(Path.of(args[0],name+".png").toFile());
            check(reference.getWidth()==2880 && reference.getHeight()==2048,"Original native PNG size");
            check(profilePass(reference,reference,open,PixelComparison.STRICT),"Reference self-comparison strict: "+name);
            check(profilePass(reference,reference,open,PixelComparison.LENIENT),"Reference self-comparison lenient: "+name);
            var blank=solid(2880,2048,0xffffff);
            check(!profilePass(reference,blank,open,PixelComparison.LENIENT),"Blank page rejected: "+name);
            var removed=copy(reference);g=removed.createGraphics();g.setColor(Color.WHITE);
            if(open)g.fillRect(1760,388,1072,1612); else g.fillRect(544,116,2336,272);
            g.dispose();
            check(!profilePass(reference,removed,open,PixelComparison.LENIENT),"Missing panel/summary rejected: "+name);
        }
        System.out.println("Passed "+checks+" pixel-comparison controls");
    }
}
