(ns ruuvi-server.database.pool
  (:use lobos.connectivity
        [clojure.tools.logging :only (debug info warn error)])
  (:import com.jolbox.bonecp.BoneCPDataSource)
  )

(defn- bonecp-connection-pool [dbh]
  (info "Create bonecp connection pool")
  (let [jdbc-url (str "jdbc:" (:subprotocol dbh) ":" (:subname dbh))
        connection-pool (doto (BoneCPDataSource.)
                          (.setDriverClass (dbh :classname))
                          (.setJdbcUrl jdbc-url))]
    (when (:user dbh) (.setUsername connection-pool (:user dbh)))
    (when (:password dbh) (.setPassword connection-pool (:password dbh)))
    connection-pool)
  )

(defn create-connection-pool [dbh]
  (bonecp-connection-pool dbh))
