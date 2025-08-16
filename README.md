# SimpleBackpacks (Fabric)

Lightweight backpacks that add extra storage per player.

## Build
- Requirements: JDK 17, Gradle (or use the Gradle wrapper if added later)
- Minecraft: 1.20.6, Fabric Loader/API

```
./gradlew build
```

## Run (Client)
```
./gradlew runClient
```

## Notes
- Command `/backpack` is registered (placeholder behavior for now).
- Items: `backpack_small`, `backpack_medium`, `backpack_large` registered.
- Persistence and GUI opening to be implemented next.

## Config
- Fabric mods do not use `plugin.yml` (that is for Bukkit/Spigot/Paper plugins).
- Configuration file location (planned): `<game_dir>/config/simplebackpacks.yml` on both client and server.
  - Example file shipped as `simplebackpacks.example.yml` (see `src/main/resources/`).
  - On first run, the mod will create `config/simplebackpacks.yml` if it does not exist (to be implemented).

### Example config (YAML)
```yaml
# SimpleBackpacks configuration
persistence:
  per_player: true          # Save backpacks per-player
  cross_world: true         # Backpacks shared across worlds
storage_sizes:
  small: 9
  medium: 18
  large: 27
commands:
  enable_admin_open: true   # Allow /backpack <player>
  enable_give: true         # Allow /backpack give <player> <type>
database:
  enabled: false
  type: mysql
  host: localhost
  port: 3306
  database: minecraft
  username: user
  password: pass
```

## Commands (planned)
### Usage
```
/backpack
/backpack <player>
/backpack give <player> <small|medium|large>
```

### Examples
```
/backpack
/backpack Steve
/backpack give Alex medium
```

### Permissions
- Fabric: uses op levels; subcommands that target others or give require permission level 2+ (operator).
- Spigot (if ported):
  - `simplebackpacks.use` — allow `/backpack`
  - `simplebackpacks.admin` — allow `/backpack <player>` and `/backpack give ...`

## Where do plugin.yml and config.yml go?
- `plugin.yml`: Only for Bukkit/Spigot/Paper. Not used in Fabric. If you later make a Spigot port, it belongs at `src/main/resources/plugin.yml` in that separate project.
- `config.yml`: For this Fabric mod, the config file will be `config/simplebackpacks.yml` in the game/server directory (created on first run). During development, an example is at `src/main/resources/simplebackpacks.example.yml`.
