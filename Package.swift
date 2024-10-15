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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.14.1/TaxKalculator.xcframework.zip",
            checksum: "2a1474e01e13d3093ee0cedab110936c635867b5364b1350290c81e424a57b15"
        ),
    ]
)
