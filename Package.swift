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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/1.1.0/TaxKalculator.xcframework.zip",
            checksum: "a345ae0145576108a0ab7538f36f24f0f5e21548b7a2ec25e0768cd6048472c5"
        ),
    ]
)
