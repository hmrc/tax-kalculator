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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.16.1/TaxKalculator.xcframework.zip",
            checksum: "54be4012c94ca557c3763db58c2cbc83472f79b54e6184a2f255a9ddd42666c5"
        ),
    ]
)
