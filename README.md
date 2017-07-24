## Abaco Development Manifests Repo

---

Provides Google Repo manifest files to help with easy cloning of git
repositories along with a set of helper scripts to aid building for Abaco
platforms.

### Usage

Ensure you have a working copy of the *Repo* tool

    curl https://storage.googleapis.com/git-repo-downloads/repo > repo && chmod +x repo

Initialise your build

```
mkdir abaco-build
cd abaco-build
python2 ../repo init -u git@towgit01:mitchellj/manifests.git -m manifests/machine_branch.xml
python2 ../repo sync
rm ../repo
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
