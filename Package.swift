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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/1.0.2/TaxKalculator.xcframework.zip",
            checksum: "66a6014825d62f1ff4da9253a9ac8bf05dcf8595fcaa67756487dbbc734081a5"
        ),
    ]
)
