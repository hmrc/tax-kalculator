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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.6.2/TaxKalculator.xcframework.zip",
            checksum: "36a43c120eabfaddc8826b8485a5f10e3a6f296b1ca11156cecb5332697490a4"
        ),
    ]
)
