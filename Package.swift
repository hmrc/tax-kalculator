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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.8.0/TaxKalculator.xcframework.zip",
            checksum: "1666d7d131b326ab6683ba15392001b3ebcad6ee4178327e8b7c73585799edee"
        ),
    ]
)
