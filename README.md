## Abaco Development Manifests Repo

---

Provides Google Repo manifest files to help with easy cloning of git
repositories along with a set of helper scripts to aid building for Abaco
platforms.

Please setup your host build environmnet as per the instructions on the [Yocto Project Quickstart](https://www.yoctoproject.org/docs/1.8/yocto-project-qs/yocto-project-qs.html) page.

### Usage

Ensure you have a working copy of the *Repo* tool

    curl https://storage.googleapis.com/git-repo-downloads/repo > repo && chmod +x repo

Initialise your build

```
mkdir abaco-build
cd abaco-build
python2 ../repo init -u https://github.com/Abaco-Systems/abaco-bsp-manifests.git -m manifests-[release|dev]/<machinename>_branch.xml
python2 ../repo sync
rm ../repo
```
i.e For SBC314 use:
```
python2 ../repo init -u https://github.com/Abaco-Systems/abaco-bsp-manifests.git -m manifests-dev/sbc314_pyro.xml
```

You should now have a directory heirarchy with all the required Abaco platform
repos checked out.

### Building

To start a build run

    ./init-new-build

### Releasing

To release, commit all repository changes and run the following command

    repo manifest -r -o machine_branch.xml

This will generate a new manifest file with the current set of git
repository commit hashes locked. This file can then be copied to the
manfiest folder with the filename as machine_branch.xml, commited
and tagged with the release version.
