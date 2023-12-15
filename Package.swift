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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.9.3/TaxKalculator.xcframework.zip",
            checksum: "bf6a179da697ea19017495f757f9f4574d4b8e6f4b17e58efd499caded58eb5a"
        ),
    ]
)
