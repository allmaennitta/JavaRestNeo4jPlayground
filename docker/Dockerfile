## Neo4J dependency: java
## get java from official repo
FROM java:latest
LABEL "Tiago Pires, tandrepires@gmail.com"

## install neo4j according to http://debian.neo4j.org/
# Import neo4j signing key
# Create an apt sources.list file
# Find out about the files in neo4j repo ; install neo4j community edition

RUN wget -O - http://debian.neo4j.org/neotechnology.gpg.key | apt-key add - && \
    echo 'deb http://debian.neo4j.org/repo stable/' > /etc/apt/sources.list.d/neo4j.list && \
    apt-get update ; apt-get install neo4j -y ; apt-get install bsdmainutils -y

## add launcher and set execute property
## clean sources
## turn on indexing: http://chrislarson.me/blog/install-neo4j-graph-database-ubuntu
## enable neo4j indexing, and set indexable keys to name,age
# enable shell server on all network interfaces

COPY launch.sh /
COPY build_auth_string.sh /
RUN chmod +x /launch.sh && chmod +x /build_auth_string.sh && \
    apt-get clean && \
    sed -i "s|#node_auto_indexing|node_auto_indexing|g" /var/lib/neo4j/conf/neo4j.properties && \
    sed -i "s|#node_keys_indexable|node_keys_indexable|g" /var/lib/neo4j/conf/neo4j.properties && \
    echo "remote_shell_host=0.0.0.0" >> /var/lib/neo4j/conf/neo4j.properties

# expose REST and shell server ports
EXPOSE 7474
EXPOSE 1337

WORKDIR /

## entrypoint
CMD ["/bin/bash", "-c", "/launch.sh"]
