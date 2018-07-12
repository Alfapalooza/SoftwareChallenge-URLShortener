import com.google.inject.AbstractModule

import net.codingwell.scalaguice.ScalaModule

import org.byrde.config.ConfigurationProvider
import org.byrde.guice.{Modules, OnStart}
import org.byrde.persistence.{Persistence, Tables}

/**
  * This class is a Guice module that tells Guice how to bind several
  * different types. This Guice module is created when the Play
  * application starts.

  * Play will automatically use any class called `Module` that is in
  * the root package. You can create modules in other locations by
  * adding `play.modules.enabled` settings to the `application.conf`
  * configuration file.
  */
class Module extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[ConfigurationProvider]
    bind[Persistence]
    bind[Tables]
    bind[Modules].asEagerSingleton()
    bind[OnStart].asEagerSingleton()
  }
}