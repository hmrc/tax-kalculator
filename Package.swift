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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.14.2/TaxKalculator.xcframework.zip",
            checksum: "2ba1de648ebb97b93c681f2a20c57afa687ba5aab5b6b3203f3384168bf0d86b"
        ),
    ]
)
