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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.1.1/TaxKalculator.xcframework.zip",
            checksum: "c27a33052048e31053bbffb071551bdf19009e4212d6331a2c59db0ad59d72c9"
        ),
    ]
)
