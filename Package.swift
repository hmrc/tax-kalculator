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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.9.0/TaxKalculator.xcframework.zip",
            checksum: "d5a004f6a9c70e6d8941b54563cad55fd544a6f34f1233add00d8736cc222220"
        ),
    ]
)
