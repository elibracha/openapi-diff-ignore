# Guidelines For Contributing

## Before submitting an issue

 - If you're not using the latest master to parse Ignore file, please give it another try by pulling the latest master as the issue may have already been addressed. Ref: [Getting Started](https://github.com/elibracha/openapi-ignore-parser)
 - Search the [open issue](https://github.com/elibracha/openapi-ignore-parser/issues) and [closed issue](https://github.com/elibracha/openapi-ignore-parser/issues) to ensure no one else has reported something similar before.
 - File an [issue ticket](https://github.com/elibracha/openapi-ignore-parser/issues/new) by providing all the required information.
 - Test with the latest master by building the JAR locally to see if the issue has already been addressed.
 - You can also make a suggestion or ask a question by opening an "issue".

## Before submitting a PR

 - Search the [open issue](https://github.com/elibracha/openapi-ignore-parser/issues) to ensure no one else has reported something similar and no one is actively working on similar proposed change.
 - If no one has suggested something similar, open an ["issue"](https://github.com/elibracha/openapi-ignore-parser/issues) with your suggestion to gather feedback from the community.
 - It's recommended to **create a new git branch** for the change so that the merge commit message looks nicer in the commit history.

## How to contribute

### Style guide
Code change should conform to the programming style guide of the respective languages:
- Java: https://google.github.io/styleguide/javaguide.html
- Kotlin: https://kotlinlang.org/docs/reference/coding-conventions.html

For other languages, feel free to suggest.
### Tips
- Smaller changes are easier to review
- [Optional] For bug fixes, provide a OpenAPI Ignore Spec to repeat the issue so that the reviewer can use it to confirm the fix
- Add test case(s) to cover the change
- Document the fix in the code to make the code more readable
- Make sure test cases passed after the change (one way is to leverage https://travis-ci.org/ to run the CI tests)
- File a PR with meaningful title, description and commit messages. A good example is [PR-3306](https://github.com/swagger-api/swagger-codegen/pull/3306)
- Recommended git settings
   - `git config --global core.autocrlf input` to tell Git convert CRLF to LF on commit but not the other way around 
- To close an issue (e.g. issue 1542) automatically after a PR is merged, use keywords "fix", "close", "resolve" in the PR description, e.g. `fix #1542`. (Ref: [closing issues using keywords](https://help.github.com/articles/closing-issues-using-keywords/))
