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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.13.0/TaxKalculator.xcframework.zip",
            checksum: "4c904fd16f1563bc826828df87b7815127d309760224ad77b793c17dc697120f"
        ),
    ]
)
