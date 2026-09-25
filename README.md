# React Native Nitro Network Logger

A React Native Nitro module for capturing, storing, and displaying recent network request and response logs on Android.

The module provides a lightweight logging layer for monitoring HTTP communication during development and debugging.

## Features

- HTTP request logging
- HTTP response logging
- Request method and URL tracking
- HTTP status code tracking
- Manual log insertion
- Native network log viewer
- Scrollable log history
- Stores the latest 10 log entries
- Android support

## Installation

Install the package using npm:

```bash
npm install react-native-nitro-network-logger
```

After installing the package, rebuild the Android application:

```bash
npx react-native run-android
```

## Usage

Import the module:

```ts
import { helloWorld } from 'react-native-nitro-network-logger'
```

### Open the Network Log Viewer

```ts
helloWorld.openLogPanels()
```

This opens the native network log viewer and displays the stored log entries.

### Add a Manual Log

Application-level events can also be added manually:

```ts
helloWorld.appendLog('User opened the product page')
```

Manual logs are stored together with automatically captured network logs.

### Get Logs

The current log entries can be retrieved using:

```ts
const logs = helloWorld.getLogs()
```

## Network Logging

The module captures HTTP request and response information from the application's networking layer.

Example:

```text
REQUEST: GET https://example.com/api/products
RESPONSE: 200
Status: 200
Response successfully received.
```

Each network request is stored as a single log entry.

## Log Limit

The module keeps the latest 10 log entries.

When a new entry is added after the limit is reached, the oldest entry is automatically removed.

This keeps the in-memory log collection limited to recent activity.

## Network Log Viewer

The module includes a native log viewer for inspecting recent network activity.

The viewer provides:

- Request count
- HTTP method
- Request URL
- Response status
- Success and failure indicators
- Scrollable recent activity
- Manual application logs

## Architecture

```text
React Native Application
          |
          v
      HTTP Request
          |
          v
   Network Interceptor
          |
          v
      appendLog()
          |
          v
   Recent Log Storage
          |
          v
   Network Log Viewer
```

## API

| Function | Description |
|---|---|
| `getMessage()` | Returns a test message from the module |
| `appendLog(log)` | Adds a manual log entry |
| `getLogs()` | Returns the stored log entries |
| `openLogPanels()` | Opens the network log viewer |

## Example

```ts
import { helloWorld } from 'react-native-nitro-network-logger'

helloWorld.appendLog('Application started')

const logs = helloWorld.getLogs()

helloWorld.openLogPanels()
```

## Android Support

Android is currently supported.

## Requirements

- React Native
- React Native Nitro Modules
- Android

## License

MIT