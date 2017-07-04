SUMMARY = "Abaco Base Image"

require recipes-core/images/core-image-minimal.bb

IMAGE_INSTALL += " \
	${MACHINE_EXTRA_RRECOMMENDS} \
	udev-extraconf \
"
