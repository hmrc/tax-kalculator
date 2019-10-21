#!/usr/bin/env bash
export GITHUB_TOKEN="20a19f0825e0b0a9d0a5434da932a91988c8783d"
if [ -z "$1" ]; then
    echo "ERROR: Please provide a git tag"
else
    if echo "$1" | grep -Eq "^\d{0,3}\.\d{1}\.\d{1}$"; then
      ./gradlew build
      DIRECTORY="Carthage"
      ZIP="TaxKalculator.framework.zip"
      if [ -d "$DIRECTORY" ]; then
        rm -rf $DIRECTORY
      fi
      if [ -f "$ZIP" ]; then
        rm $ZIP
      fi
      mkdir $DIRECTORY && mkdir $DIRECTORY/Build && mkdir $DIRECTORY/Build/iOS
      cp -r build/xcode-frameworks/TaxKalculator* $DIRECTORY/Build/iOS
      zip -r TaxKalculator.framework.zip $DIRECTORY
      echo "ghr run step"
#      ghr $1 $ZIP
    else
      echo "ERROR: Please use a valid git tag format e.g 1.0.3"
    fi
fi