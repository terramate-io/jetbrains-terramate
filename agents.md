# Agents Guide - Terramate JetBrains Plugin

This document provides comprehensive instructions for AI agents and developers working on the Terramate IntelliJ IDEA plugin.

## Table of Contents

- [Project Overview](#project-overview)
- [Development Setup](#development-setup)
- [Project Structure](#project-structure)
- [Development Workflow](#development-workflow)
- [Build System](#build-system)
- [Testing and Verification](#testing-and-verification)
- [Changelog Management](#changelog-management)
- [Release Process](#release-process)
- [Important Conventions](#important-conventions)
- [Troubleshooting](#troubleshooting)

## Project Overview

**Terramate JetBrains Plugin** is an IntelliJ IDEA plugin that provides language support for Terramate configuration files through:
- Syntax highlighting for `.tm`, `.tm.hcl`, and `.tmgen` files
- Language Server Protocol (LSP) integration with `terramate-ls`
- Code completion, validation, diagnostics, and navigation features
- Configurable language server settings

### Technology Stack

- **Language**: Kotlin
- **Build Tool**: Gradle 8.13+
- **IntelliJ Platform**: 2024.2+ (minimum)
- **Java Version**: 21
- **LSP Integration**: LSP4IJ 0.13.0
- **Plugin Framework**: IntelliJ Platform Gradle Plugin 2.10.4

## Development Setup

### Prerequisites

1. **Java 21** - Required for building and running
2. **Gradle 8.13+** - Included via Gradle wrapper
3. **IntelliJ IDEA** - Recommended for development (Community or Ultimate)
4. **terramate-ls** - Language server binary (for testing LSP features)

### Initial Setup

```bash
# Clone the repository
git clone https://github.com/terramate-io/terramate-jetbrains.git
cd terramate-jetbrains

# Make gradlew executable
chmod +x gradlew

# Build the project
./gradlew build

# Run the plugin in a sandbox IDE
./gradlew runIde
```

## Project Structure

```
terramate-jetbrains/
├── .github/
│   └── workflows/
│       ├── build.yml          # CI: Build and test on PRs
│       └── release.yml        # CD: Publish to JetBrains Marketplace
├── src/
│   └── main/
│       ├── kotlin/
│       │   └── io/terramate/intellij/
│       │       ├── TerramateFileType.kt        # File type definition
│       │       ├── TerramateLanguage.kt        # Language definition
│       │       ├── TerramateTextMateLanguage.kt # TextMate integration
│       │       ├── TerramateIconProvider.kt    # File icons
│       │       ├── lsp/
│       │       │   ├── TerramateLSPClientFeatures.kt
│       │       │   └── TerramateLSPServerSupportProvider.kt
│       │       ├── settings/
│       │       │   ├── TerramateSettings.kt    # Settings state
│       │       │   └── TerramateSettingsConfigurable.kt # Settings UI
│       │       └── actions/
│       │           └── RestartLanguageServerAction.kt
│       └── resources/
│           ├── META-INF/
│           │   └── plugin.xml              # Plugin configuration
│           ├── textmate/
│           │   └── terramate.tmLanguage.json # Syntax highlighting
│           └── icons/                       # Plugin icons
├── build.gradle.kts                        # Build configuration
├── gradle.properties                       # Gradle properties
├── settings.gradle.kts                     # Gradle settings
├── CHANGELOG.md                            # Version history
└── agents.md                               # This file

```

### Key Files

#### `build.gradle.kts`
- Plugin dependencies and versions
- IntelliJ Platform configuration
- Plugin verification settings
- Publishing configuration

#### `src/main/resources/META-INF/plugin.xml`
- Plugin metadata (name, description, vendor)
- Extension points registration
- Action definitions
- File type associations

#### `src/main/resources/textmate/terramate.tmLanguage.json`
- TextMate grammar for syntax highlighting
- Defines tokens, patterns, and scopes for Terramate syntax

## Development Workflow

### Making Code Changes

1. **Create a feature branch**:
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **Make your changes** following these guidelines:
   - Use Kotlin idioms and conventions
   - Follow existing code style
   - Add comments for complex logic
   - Avoid deprecated IntelliJ Platform APIs

3. **Test your changes**:
   ```bash
   # Run the plugin in sandbox
   ./gradlew runIde
   
   # Run tests
   ./gradlew test
   
   # Verify plugin compatibility
   ./gradlew verifyPlugin
   ```

4. **Check for linter errors**:
   ```bash
   ./gradlew check
   ```

### Plugin Compatibility

The plugin must be compatible with:
- **Minimum Version**: IntelliJ IDEA 2024.2 (build 242)
- **Target Versions**: All future versions (no `untilBuild` limit)

Always run `./gradlew verifyPlugin` to check compatibility before submitting changes.

## Build System

### Gradle Tasks

Common tasks:

```bash
# Build the plugin
./gradlew buildPlugin

# Run plugin in sandbox IDE
./gradlew runIde

# Run tests
./gradlew test

# Verify plugin compatibility
./gradlew verifyPlugin

# Build and verify everything
./gradlew clean build verifyPlugin

# Publish to JetBrains Marketplace (requires PUBLISH_TOKEN)
./gradlew publishPlugin
```

### Configuration Files

#### `build.gradle.kts`
Key configurations:
- **Plugin version**: `version = "0.0.1"`
- **Minimum IDE**: `sinceBuild = "242"`
- **Target IDE**: `intellijIdeaCommunity("2024.2")`
- **Dependencies**: LSP4IJ plugin, Kotlin stdlib

#### `gradle/wrapper/gradle-wrapper.properties`
- **Gradle version**: 8.13 (required for IntelliJ Platform Plugin 2.10.4)

## Testing and Verification

### Manual Testing

1. **Run in Sandbox**:
   ```bash
   ./gradlew runIde
   ```
   This launches a separate IntelliJ IDE instance with the plugin installed.

2. **Test Features**:
   - Create/open `.tm`, `.tm.hcl`, or `.tmgen` files
   - Check syntax highlighting
   - Verify LSP features (requires `terramate-ls` in PATH or configured)
   - Test settings in `Settings > Languages & Frameworks > Terramate`

### Plugin Verification

```bash
./gradlew verifyPlugin
```

This verifies:
- Plugin structure and configuration
- Compatibility with target IDE versions
- Absence of deprecated API usage
- Plugin size and dependencies

**Important**: Plugin verification must pass with NO deprecated API warnings before release.

## Changelog Management

### CHANGELOG.md Format

The project uses [Keep a Changelog](https://keepachangelog.com/) format with [Semantic Versioning](https://semver.org/).

### Categories

- **Added**: New features
- **Changed**: Changes to existing functionality
- **Deprecated**: Soon-to-be removed features
- **Removed**: Removed features
- **Fixed**: Bug fixes
- **Security**: Security fixes

### Updating the Changelog

1. **During Development**: Add changes to `[Unreleased]` section:
   ```markdown
   ## [Unreleased]
   
   ### Added
   - New feature description
   
   ### Fixed
   - Bug fix description
   ```

2. **Before Release**: Move unreleased changes to a new version section:
   ```markdown
   ## [0.0.2] - 2025-11-21
   
   ### Added
   - Feature that was in Unreleased
   
   ## [Unreleased]
   
   (empty or new changes)
   ```

3. **Update Links**: Add version comparison link at bottom:
   ```markdown
   [Unreleased]: https://github.com/terramate-io/terramate-jetbrains/compare/v0.0.2...HEAD
   [0.0.2]: https://github.com/terramate-io/terramate-jetbrains/releases/tag/v0.0.2
   ```

## Release Process

### Overview

The release process is **semi-automated**:
1. **Manual**: Create GitHub release via UI
2. **Automatic**: Workflow publishes to JetBrains Marketplace

### Step-by-Step Release

#### 1. Prepare the Release

```bash
# Ensure you're on main branch
git checkout main
git pull origin main

# Update version in build.gradle.kts
# Change: version = "0.0.2"

# Update CHANGELOG.md
# Move [Unreleased] changes to new version section

# Commit version bump
git add build.gradle.kts CHANGELOG.md
git commit -m "chore: bump version to 0.0.2"
git push origin main
```

#### 2. Create GitHub Release

1. Go to: https://github.com/terramate-io/terramate-jetbrains/releases/new
2. Click "Choose a tag" → Create new tag: `v0.0.2`
3. Set release title: `v0.0.2`
4. Generate release notes or copy from CHANGELOG.md
5. *Optional*: Attach `build/distributions/Terramate-0.0.2.zip` (build locally first)
6. Click "Publish release"

#### 3. Automatic Publishing

The `.github/workflows/release.yml` workflow automatically:
1. Triggers on release publication
2. Checks out the code
3. Builds the plugin
4. Verifies compatibility
5. Publishes to JetBrains Marketplace (using `JETBRAINS_MARKETPLACE_TOKEN` secret)

#### 4. Verify Publication

1. **GitHub Release**: Check release appears at `/releases`
2. **Marketplace**: Check https://plugins.jetbrains.com/plugin/YOUR_PLUGIN_ID
3. **Monitor**: Check workflow run in Actions tab

### Required Secrets

Set in GitHub repository settings:

- `JETBRAINS_MARKETPLACE_TOKEN`: Token for publishing to marketplace
  - Get from: https://plugins.jetbrains.com/author/me/tokens

### Manual Publishing (if needed)

```bash
# Set environment variable
export PUBLISH_TOKEN="your-jetbrains-marketplace-token"

# Publish
./gradlew publishPlugin
```

## Important Conventions

### Code Style

- **Kotlin**: Follow standard Kotlin conventions
- **Indentation**: 4 spaces
- **Line Length**: Soft limit at 120 characters
- **Naming**: 
  - Classes: PascalCase
  - Functions: camelCase
  - Constants: UPPER_SNAKE_CASE

### API Usage

- **Always use current APIs**: Avoid deprecated IntelliJ Platform APIs
- **Check compatibility**: Run `verifyPlugin` to catch deprecated usage
- **Reference documentation**: https://plugins.jetbrains.com/docs/intellij/

### Common Deprecated APIs to Avoid

| Deprecated | Use Instead |
|------------|-------------|
| `FileChooserDescriptorFactory.createSingleFileDescriptor()` | `FileChooserDescriptor(true, false, false, false, false, false)` |
| `addBrowseFolderListener(String, String, Project, FileChooserDescriptor)` | `addBrowseFolderListener(TextBrowseFolderListener)` |
| `ide(String)` in verification | `recommended()` or `select { }` with criteria |

### Git Workflow

- **Main branch**: `main` - always deployable
- **Feature branches**: `feature/description` or `fix/description`
- **Commit messages**: Follow Conventional Commits
  - `feat:` - New feature
  - `fix:` - Bug fix
  - `chore:` - Maintenance
  - `docs:` - Documentation
  - `refactor:` - Code refactoring

Example:
```
feat: add support for Terramate v0.5 syntax
fix: resolve LSP connection timeout issue
chore: update IntelliJ Platform to 2024.3
```

## Troubleshooting

### Common Issues

#### Build Fails with "Cannot find Java Runtime"

**Solution**: Ensure Java 21 is installed and JAVA_HOME is set:
```bash
export JAVA_HOME=/path/to/java21
./gradlew build
```

#### Plugin Verification Fails with Deprecated API Warnings

**Solution**: Check the verification report and update to current APIs:
```bash
./gradlew verifyPlugin
# Check: build/reports/pluginVerifier/
```

#### Gradle Version Incompatibility

**Error**: `IntelliJ Platform Gradle Plugin requires Gradle 8.13 and higher`

**Solution**: Update `gradle/wrapper/gradle-wrapper.properties`:
```properties
distributionUrl=https\://services.gradle.org/distributions/gradle-8.13-bin.zip
```

#### LSP Not Working in Sandbox

**Solution**: Ensure `terramate-ls` is available:
1. Install `terramate-ls`: https://terramate.io/docs/cli/installation
2. Or configure custom path in plugin settings

#### Plugin Not Loading in Sandbox

**Check**:
1. `plugin.xml` is valid XML
2. All extension points are correctly registered
3. Check `idea.log` in sandbox: `build/idea-sandbox/system/log/idea.log`

### Getting Help

- **IntelliJ Platform Docs**: https://plugins.jetbrains.com/docs/intellij/
- **LSP4IJ Docs**: https://github.com/redhat-developer/lsp4ij
- **IntelliJ Platform SDK**: https://github.com/JetBrains/intellij-community
- **Terramate Docs**: https://terramate.io/docs

## Development Tips

### Hot Reload

Some changes (like Kotlin code) require restarting the sandbox IDE. Others (like `plugin.xml`) may auto-reload.

### Debugging

1. Run sandbox in debug mode:
   ```bash
   ./gradlew runIde --debug-jvm
   ```

2. Attach debugger to port 5005 in your IDE

### Testing Changes Quickly

```bash
# Quick rebuild and launch
./gradlew buildPlugin runIde
```

### Checking Plugin Size

```bash
ls -lh build/distributions/*.zip
```

Keep plugin size reasonable (<10MB ideally, excluding dependencies).

## Maintenance

### Regular Updates

Periodically update:
- IntelliJ Platform version (when new major releases come out)
- LSP4IJ plugin version
- Gradle version
- Dependencies

### Compatibility Testing

Before major releases, test on:
- Minimum supported version (IntelliJ IDEA 2024.2)
- Latest stable version
- Beta/EAP versions (if available)

### Monitoring

Watch for:
- Deprecation warnings in build output
- Plugin verification failures
- User-reported issues on GitHub

---

## Quick Reference

### Development Cycle

```bash
# 1. Make changes
vim src/main/kotlin/...

# 2. Test locally
./gradlew runIde

# 3. Verify
./gradlew verifyPlugin

# 4. Build
./gradlew build

# 5. Update changelog
vim CHANGELOG.md

# 6. Commit and push
git add .
git commit -m "feat: your feature"
git push
```

### Release Cycle

```bash
# 1. Update version
vim build.gradle.kts

# 2. Update changelog
vim CHANGELOG.md

# 3. Commit
git commit -m "chore: bump version to X.Y.Z"
git push

# 4. Create GitHub release (via UI)
# → Workflow automatically publishes to marketplace
```

---

**Last Updated**: 2025-11-21
**Plugin Version**: 0.0.1
**Gradle Version**: 8.13
**IntelliJ Platform**: 2024.2+

