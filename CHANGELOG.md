# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [0.0.1] - 2025-10-31

### Added

- Initial release of Terramate plugin for IntelliJ IDEA
- Syntax highlighting for Terramate files (.tm, .tm.hcl, .tmgen)
- Language Server Protocol (LSP) integration with terramate-ls
- Code completion and validation through LSP
- Real-time diagnostics and error reporting
- Go to Definition navigation
- Hover documentation support
- File type detection for Terramate configuration files
- Code folding for Terramate blocks
- Bracket matching for HCL-style syntax
- Comment toggling support (line and block comments)
- Configurable language server settings
- Custom language server path configuration
- Automatic language server discovery from PATH
- Restart Language Server action in Tools menu
- Custom file icons for Terramate files
- Support for IntelliJ IDEA 2024.2 and newer
- Support for Terramate Pro features (bundles, components, scaffolding)

### Dependencies

- Requires terramate-ls language server to be installed
- Built on IntelliJ Platform 2024.2
- Uses LSP4IJ 0.13.0 for Language Server Protocol support

[Unreleased]: https://github.com/terramate-io/terramate-jetbrains/compare/v0.0.1...HEAD
[0.0.1]: https://github.com/terramate-io/terramate-jetbrains/releases/tag/v0.0.1

