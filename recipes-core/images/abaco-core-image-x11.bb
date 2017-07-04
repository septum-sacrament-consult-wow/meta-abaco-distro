DESCRIPTION = "Abaco Core Image X11"

require abaco-core-image.bb
inherit distro_features_check

REQUIRED_DISTRO_FEATURES = "x11"

IMAGE_FEATURES += " \
	x11-base \
"

IMAGE_INSTALL += " \
	mesa-demos \
"
