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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.9.1/TaxKalculator.xcframework.zip",
            checksum: "782207f2efecf9ccc2f651aa5fd47a221d2243d73e32fd10de55038bc025d996"
        ),
    ]
)
