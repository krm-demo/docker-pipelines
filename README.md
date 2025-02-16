# docker-pipelines #
This repository demonstrates the simplest command-line applications,
which can be containerized and published with [docker tool](https://docs.docker.com/). 
The functionality of those applications is just output the information
about build- and runtime- environment, sush as system-valriables and java-system-properties.

Once a considerable number of [bash](https://www.gnu.org/software/bash/)-scripts are exexuted during the build,
it's highly recommended to have the latest version of [GNU core utilities](https://www.gnu.org/software/coreutils/).
There's nothing top do at CI/CD side (bitbucket or git-hub), but following additional steps are required locally:

### Upgrading [bash](https://www.gnu.org/software/bash/) for [Mac](https://en.wikipedia.org/wiki/Mac_operating_systems)
By default [Mac's Terminal](https://support.apple.com/guide/terminal/welcome/mac) has an outdated version of `bash`-utilities
and it's necessary to upgrade the using [Homebrew](https://brew.sh/):
* before upgrading you can see following output:
```bash
...$ which date
/bin/date
...$ data --version
date: illegal option -- -
usage: date [-jnRu] [-I[date|hours|minutes|seconds]] [-f input_fmt]
            [-r filename|seconds] [-v[+|-]val[y|m|w|d|H|M|S]]
            [[[[mm]dd]HH]MM[[cc]yy][.SS] | new_date] [+output_fmt]
```
* so, let's install [GNU core utilities](https://www.gnu.org/software/coreutils/) using `brew install coreutils` command:
```bash
...$ brew install coreutils
==> Auto-updating Homebrew...
==> Auto-updated Homebrew!
==> New Formulae
. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
==> Downloading https://ghcr.io/v2/homebrew/core/coreutils/manifests/9.6
. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
==> Downloading https://ghcr.io/v2/homebrew/core/gmp/manifests/6.3.0
. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
==> Downloading https://ghcr.io/v2/homebrew/core/gmp/blobs/sha256:6683d73d6677d28e1e8d1b92d6ebfbc068c1d33e19b79114a22a648a99ba5991
. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
==> Downloading https://ghcr.io/v2/homebrew/core/coreutils/blobs/sha256:637db884c3812f07eeebcb05ab48ef4a248d646abd8279aa29f1e78669fd99b4
. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
==> Downloading https://ghcr.io/v2/homebrew/core/gmp/manifests/6.3.0
. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
==> Pouring gmp--6.3.0.arm64_sequoia.bottle.tar.gz
==> Installing coreutils
. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
Commands also provided by macOS and the commands dir, dircolors, vdir have been installed with the prefix "g".
If you need to use these commands with their normal names, you can add a "gnubin" directory to your PATH with:
  PATH="/opt/homebrew/opt/coreutils/libexec/gnubin:$PATH"
```
* after that it'a necessary to add the path to just installed utilities in `~/.bash_profile` like it's recommended in the end of installation
* then after restarting (or reloading) the terminal you must see following:
```bash
...$ which date
/opt/homebrew/opt/coreutils/libexec/gnubin/date
...$ date --version
date (GNU coreutils) 9.6
Copyright (C) 2025 Free Software Foundation, Inc.
License GPLv3+: GNU GPL version 3 or later <https://gnu.org/licenses/gpl.html>.
This is free software: you are free to change and redistribute it.
There is NO WARRANTY, to the extent permitted by law.

Written by David MacKenzie.

```
* **congratulations!** `date` and `realpath` utilities will work as expected since now!

### Using [bash](https://www.gnu.org/software/bash/) for [Windows](https://en.wikipedia.org/wiki/Microsoft_Windows)
Nothing was verified at Windows, but following links could help:
* https://superuser.com/questions/920853/more-recent-build-of-gnu-coreutils-for-win32
* https://git-scm.com/downloads/win
* the latest installtion of [git](https://gitforwindows.org/)-tool with up-to-date version of [git-bash](https://www.atlassian.com/git/tutorials/git-bash)
