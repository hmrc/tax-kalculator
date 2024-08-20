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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.14.0/TaxKalculator.xcframework.zip",
            checksum: "2c9520609e56592dc4a403ebeb3c29bbc6320e406bf5cbf3fa8220bd187882e0"
        ),
    ]
)
