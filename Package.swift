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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.3.0/TaxKalculator.xcframework.zip",
            checksum: "9e94d11e4e21f3d53818a8e8bd655aa6b629ca68cf38d29d90de02558759e3af"
        ),
    ]
)
