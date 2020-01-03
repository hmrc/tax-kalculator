#!/usr/bin/env bash
if [ -z "$2" ]; then
  echo "ERROR: Please provide a Github token"
else
  export export GITHUB_TOKEN=$2
  if [ -z "$1" ]; then
      echo "ERROR: Please provide a git tag"
  else
      if echo "$1" | grep -Eq "^\d{0,3}\.\d{1}\.\d{1}(-(alpha|beta))?$"; then
        DIRECTORY="Carthage"
        ZIP="TaxKalculator.framework.zip"
        if [ -d "$DIRECTORY" ]; then
          rm -rf $DIRECTORY
        fi
        if [ -f "$ZIP" ]; then
          rm $ZIP
        fi
        echo "INFO: Creating Carthage folder structure"
        mkdir $DIRECTORY && mkdir $DIRECTORY/Build && mkdir $DIRECTORY/Build/iOS
        echo "INFO: Remove simulator supported platform from Info.plist"
        plutil -remove "CFBundleSupportedPlatforms" build/xcode-frameworks/TaxKalculator.framework/Info.plist
        plutil -insert "CFBundleSupportedPlatforms" -xml '<array/>' build/xcode-frameworks/TaxKalculator.framework/Info.plist
        plutil -insert CFBundleSupportedPlatforms.0 -string 'iPhoneOS' build/xcode-frameworks/TaxKalculator.framework/Info.plist
        echo "INFO: Copying artifact"
        cp -r build/xcode-frameworks/TaxKalculator* $DIRECTORY/Build/iOS
        echo "INFO: Zipping"
        zip -r TaxKalculator.framework.zip $DIRECTORY
        echo "INFO: Adding zipped artifact to release"
        ghr "$1" $ZIP
      else
        echo "ERROR: Please use a valid git tag format e.g 1.0.3"
        exit 1
      fi
  fi
fi