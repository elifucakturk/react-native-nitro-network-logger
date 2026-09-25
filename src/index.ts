
import { NitroModules } from 'react-native-nitro-modules'
import type { HelloWorld } from './specs/HelloWorld.nitro'

export const helloWorld =
  NitroModules.createHybridObject<HelloWorld>('HelloWorld')