// swift-tools-version:5.3
import PackageDescription

let package = Package(
    name: "TaxKalculator",
    platforms: [
        .iOS(.v11)
    ],
    products: [
        .library(
            name: "TaxKalculator",
            targets: ["TaxKalculator"]
        ),
    ],
    targets: [
        .binaryTarget(
            name: "TaxKalculator",
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.6.0/TaxKalculator.xcframework.zip",
            checksum: "90871778af87a710ae3d334e9d318893d68a0e568b7d2cc318ec083be8a63c8e"
        ),
    ]
)
