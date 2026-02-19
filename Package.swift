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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.16.0/TaxKalculator.xcframework.zip",
            checksum: "bdd112412f2d30451e09fa4a0093f613c5eff4bdf428bd51aa611c7120376ce1"
        ),
    ]
)
