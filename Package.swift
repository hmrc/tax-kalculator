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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.15.0/TaxKalculator.xcframework.zip",
            checksum: "96b1d089ac59ce0614bd0f94ac81e436714655146912023ab0dfa47a60bd19e3"
        ),
    ]
)
