## Abaco Manifests

---

Provides Google Repo manifest files to help with easy cloning of git
repositories along with a set of helper scripts to aid building for Abaco
platforms.

### Usage

Ensure you have a working copy of the *Repo* tool

``` curl https://storage.googleapis.com/git-repo-downloads/repo > repo && chmod +x repo ```

Initialise your build

```
mkdir abaco-build
cd abaco-build
python2 ../repo init -u git@towgit01:mitchellj/manifests.git
python2 ../repo sync
.repo/manifests/scripts/init-build
rm ../repo
```

You should now have a directory heirarchy with all the required Abaco platform
repos checked out.
