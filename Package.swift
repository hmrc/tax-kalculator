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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.11.0/TaxKalculator.xcframework.zip",
            checksum: "f51d7685bfa7d86f82308a339ba82d1d99e972f7104efeef875b72dc832725a4"
        ),
    ]
)
