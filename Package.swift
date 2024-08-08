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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.13.2/TaxKalculator.xcframework.zip",
            checksum: "dd6b4dfaf1c95317330d58540b66803fe38684a0a406ba824c20202a1d5561d4"
        ),
    ]
)
