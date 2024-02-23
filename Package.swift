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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.10.0/TaxKalculator.xcframework.zip",
            checksum: "2933be70242a64a8e7c48122c2057a678abd40e72a00c81ade5114c9f3bb8ab8"
        ),
    ]
)
