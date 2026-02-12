# plan

0. [x] load the instruction map
1. [x] go over line by line and ignore comments and clean whitespace
2. [x] do the `#include` until no more
3. [x] register all the `#define`
4. [x] go over line by line and replace variables `$var` with their values from the `#define`
5. [ ] go over again and calcualte size of each label and add it if missing or throw exception if wrong
6. [ ] initialize `ArrayList<short>`
7. [ ] go over line by line and add the short value of each instruction to the `ArrayList<short>`
8. [ ] convert `ArrayList<short>` to `short[]`
9. [ ] write to file
