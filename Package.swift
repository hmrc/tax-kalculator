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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.13.1/TaxKalculator.xcframework.zip",
            checksum: "d58a6d23ebfa0289e37d06006027a2422101ff17f4b354e422de6178fd390d9b"
        ),
    ]
)
