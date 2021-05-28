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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/1.2.0/TaxKalculator.xcframework.zip",
            checksum: "2e01e925453264fb2f334880be2920bb051c7b180ad62a0d9f6914f471f84475"
        ),
    ]
)
