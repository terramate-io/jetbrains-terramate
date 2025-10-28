# Terramate IntelliJ Plugin

Official [Terramate](https://github.com/terramate-io/terramate) plugin for IntelliJ IDEA and other JetBrains IDEs.

This plugin provides comprehensive support for Terramate configuration files with syntax highlighting, code completion, validation, and more through Language Server Protocol (LSP) integration.

## Features

### Syntax Highlighting
Full syntax highlighting for all Terramate language constructs:
- **Core blocks**: `terramate`, `stack`, `globals`, `generate_hcl`, `generate_file`, `assert`, `output`, `script`, `vendor`, `sharing_backend`, and more
- **Pro blocks**: `define bundle`, `define component`, `scaffolding`

### Language Server Integration
When `terramate-ls` is installed, you get:
- Real-time validation and diagnostics
- Smart code completion
- Hover documentation
- Go to definition
- Error and warning reporting
- Bundle/Component support (depends on your `terramate-ls` version)

### File Support
- `.tm` files
- `.tm.hcl` files
- `.tmgen` files

## Installation

### Step 1: Install terramate-ls

Install the Terramate language server:
- **[Official installation guide](https://terramate.io/docs/cli/installation)**
- Or via Homebrew: `brew install terramate-io/tap/terramate`

The plugin automatically detects `terramate-ls` in your PATH.

### Step 2: Install the Plugin

#### From JetBrains Marketplace (Coming Soon)
1. Open IntelliJ IDEA
2. Go to **Settings/Preferences** → **Plugins**
3. Search for "Terramate"
4. Click **Install**
5. Restart the IDE

#### From Source
1. Clone this repository
2. Run `./gradlew buildPlugin`
3. Install the plugin from disk: **Settings/Preferences** → **Plugins** → ⚙️ → **Install Plugin from Disk**
4. Select the generated ZIP file in `build/distributions/`
5. Restart the IDE

## Configuration

### Settings Location
**Settings/Preferences** → **Languages & Frameworks** → **Terramate**

### Available Settings

| Setting | Default | Description |
|---------|---------|-------------|
| Enable Language Server | `true` | Enable/disable language server integration |
| Binary Path | `""` | Custom path to terramate-ls (leave empty for auto-detection) |
| Arguments | `-mode=stdio` | Command-line arguments for terramate-ls |
| Trace Level | `off` | LSP trace level: off, messages, or verbose |

### Configuration Examples

**Auto-detect terramate-ls from PATH** (Default)
```
No configuration needed - the plugin finds terramate-ls automatically
```

**Custom Binary Path**
```
Binary Path: /custom/path/to/terramate-ls
```

**Debug Language Server Issues**
```
Trace Level: verbose
```
View logs in: **Help** → **Show Log in Finder/Explorer**

## Compatibility

- **IDE**: IntelliJ IDEA 2024.2+ (Community and Ultimate)
- **Also supports**: PyCharm, WebStorm, GoLand, and other JetBrains IDEs 2024.2+
- **JDK**: Java 17 or later required for development

## Related Projects

- [Terramate CLI](https://github.com/terramate-io/terramate) - Terramate command-line tool
- [VSCode Extension](https://github.com/terramate-io/vscode-terramate) - Terramate extension for Visual Studio Code
- [terramate-ls](https://github.com/terramate-io/terramate) - Terramate Language Server

## License

Apache 2.0 - See [LICENSE](LICENSE) for details.

## Support

- [Documentation](https://terramate.io/docs)
- [Discord Community](https://terramate.io/discord)
- [GitHub Issues](https://github.com/terramate-io/intellij-terramate/issues)
