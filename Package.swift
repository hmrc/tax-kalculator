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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.2.0/TaxKalculator.xcframework.zip",
            checksum: "6945af5d6104bee3c8b4258dffea12cff5db1b38fe262e8ea23085ddc5112e5c"
        ),
    ]
)
