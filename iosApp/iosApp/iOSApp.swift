import SwiftUI
import ComposeApp


@main
struct iOSApp: App {
    init() {
        AppModuleKt.doInitKoin(appDeclaration: { _ in })
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}