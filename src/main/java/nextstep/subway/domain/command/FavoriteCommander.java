package nextstep.subway.domain.command;

import lombok.RequiredArgsConstructor;
import nextstep.subway.domain.entity.favorite.Favorite;
import nextstep.subway.domain.query.PathReader;
import nextstep.subway.domain.repository.FavoriteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteCommander {
    private final FavoriteRepository favoriteRepository;
    private final PathReader pathReader;

    @Transactional
    public Long createFavorite(FavoriteCommand.CreateFavorite command) {
        verifyPathExist(command.getSource(), command.getTarget());
        Favorite favorite = Favorite.create(command);
        favoriteRepository.save(favorite);
        return favorite.getId();
    }

    @Transactional
    public void deleteFavorite(FavoriteCommand.DeleteFavorite command) {
        Favorite favorite = favoriteRepository.findByIdOrThrow(command.getFavoriteId());
        favorite.verifyOwner(command.getMemberId());
        favoriteRepository.deleteById(favorite.getId());
    }

    private void verifyPathExist(Long source, Long target) {
        pathReader.findShortestPath(source, target);
    }
}
