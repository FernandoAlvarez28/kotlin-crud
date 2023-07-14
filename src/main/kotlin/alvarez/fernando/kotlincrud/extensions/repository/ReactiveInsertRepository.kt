package alvarez.fernando.kotlincrud.extensions.repository

import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.data.r2dbc.core.R2dbcEntityOperations
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.util.Assert
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@NoRepositoryBean
abstract class ReactiveInsertRepository<T, ID>(private val entityOperations: R2dbcEntityOperations) {
    //R2dbcRepository's save doesn't identify if an entity needs to be inserted or just updated, so we need these methods

    /**
     * Inserts a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity must not be `null`.
     * @return [Mono] emitting the saved entity.
     * @throws IllegalArgumentException in case the given [entity] is `null`.
     * @throws OptimisticLockingFailureException when the entity uses optimistic locking and has a version attribute with
     * a different value from that found in the persistence store.
     */
    open fun <S : T> insert(entity: S): Mono<S> {
        Assert.notNull(entity, "Object to save must not be null")
        return this.entityOperations.insert(entity)
    }

    /**
     * Inserts all given entities.
     *
     * @param entities must not be `null`.
     * @return [Flux] emitting the saved entities.
     * @throws IllegalArgumentException in case the given [entities] or one of its entities is
     *           `null`.
     * @throws OptimisticLockingFailureException when at least one entity uses optimistic locking and has a version
     *           attribute with a different value from that found in the persistence store. Also thrown if at least one
     *           entity is assumed to be present but does not exist in the database.
     */
    open fun <S : T> insert(entities: Iterable<S>): Flux<S> {
        Assert.notNull(entities, "Objects to save must not be null")
        return Flux.fromIterable(entities).concatMap(this::insert)
    }

}