import { type HybridObject } from 'react-native-nitro-modules'

export interface HelloWorld extends HybridObject<{
  android: 'kotlin'
}> {
  getMessage(): string
  appendLog(log: string): boolean
  openLogPanels(): void
  getLogs(): string[]
  
}
