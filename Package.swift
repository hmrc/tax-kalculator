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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.10.2/TaxKalculator.xcframework.zip",
            checksum: "b48a0f256986533614663306e8bffedaa79ccd885e899a2bd9ec2ef5b7001590"
        ),
    ]
)
