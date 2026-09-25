package com.margelo.nitro.HelloWorld
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.model.ReactModuleInfoProvider
import com.facebook.react.BaseReactPackage
import com.facebook.react.modules.network.NetworkingModule
import com.facebook.react.modules.network.CustomClientBuilder

class NitroHelloworldPackage : BaseReactPackage() {
    override fun getModule(name: String, reactContext: ReactApplicationContext): NativeModule? = null

    override fun getReactModuleInfoProvider(): ReactModuleInfoProvider = ReactModuleInfoProvider { HashMap() }

    companion object {
        init {
            NitroHelloworldOnLoad.initializeNative()
           NetworkingModule.setCustomClientBuilder(
    CustomClientBuilder { builder ->
        builder.addNetworkInterceptor(
            NetworkLoggerInterceptor()
        )
    }
)
    }
}
}