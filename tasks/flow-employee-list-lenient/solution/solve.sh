#!/bin/bash
set -euo pipefail
APP_DIR=${APP_DIR:-/app}
SOLUTION_DIR=$(cd "$(dirname "$0")" && pwd)
cp -R "$SOLUTION_DIR/app/." "$APP_DIR/"
