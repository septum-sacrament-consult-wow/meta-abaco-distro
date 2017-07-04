require abaco-test-image.bb

DESCRIPTION = "Abaco Dev Image"

IMAGE_FEATURES += " dev-pkgs"

IMAGE_INSTALL += " \
	git \
	packagegroup-core-buildessential \
"
