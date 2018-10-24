SUMMARY = "Abaco Base Image"

require recipes-core/images/core-image-minimal.bb

IMAGE_INSTALL += " \
	packagegroup-base \
	dosfstools \
	ethtool \
	iperf3 \
	mtd-utils \
	nano \
	tftp-hpa \
	util-linux \
	wget \
"
