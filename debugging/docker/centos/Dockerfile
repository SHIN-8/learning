FROM centos

# ---- add build tools
RUN yum update -y && \
    yum upgrade -y && \
    yum groupinstall -y "Development Tools"  && \
    yum install -y kernel-devel kernel-headers