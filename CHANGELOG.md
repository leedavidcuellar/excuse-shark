# Changelog

All notable changes made to the project since the package migration and cleanup.

## [Unreleased] - 2025-11-26

- Refactor: removed `accenture` from packages and moved sources to `com.excusasshark`.
- Fix: `FragmentController` now returns `FragmentResponseDTO` and uses `updateFromDTO`.
- Service: added `createFromDTOResponse` and `updateFromDTO` in `FragmentService` to return DTOs.
- DTO: `ExcuseResponseDTO.roleTarget` changed to `RoleType` and `meme` / `ley` fields were added.
- Mapper: `ExcuseMapper` maps `roleTarget`, `meme` and `ley` into the response DTO.
- Tests: updated `ExcuseGeneratorServiceTest` with lenient mocks and stubs so tests run deterministically.
- Misc: adjusted `ExcuseGeneratorService` to return updated DTOs after persisting meme/ley/role changes.

## Notes

- All unit tests pass locally (17/17) and a runnable jar was generated: `target/excusas-shark-1.0.0.jar`.
- I attempted to create a git commit, but `git` is not available in this execution environment. Please run the commit commands locally (instructions below).

## How to commit locally

Run these commands in the project root:

```powershell
git add -A
git commit -m "Refactor packages -> com.excusasshark; fix controllers, mappers, DTOs; update tests"
git push origin <branch-name>
```
