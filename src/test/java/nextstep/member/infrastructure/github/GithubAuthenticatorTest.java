package nextstep.member.infrastructure.github;

import autoparams.AutoSource;
import nextstep.fake.github.GithubStaticUsers;
import nextstep.member.domain.command.authenticator.SocialOAuthAuthenticateCommand;
import nextstep.member.domain.command.authenticator.SocialOAuthProvider;
import nextstep.member.domain.command.authenticator.SocialOAuthUser;
import nextstep.util.BaseTestSetup;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class GithubAuthenticatorTest extends BaseTestSetup {

    @Autowired
    GithubAuthenticator sut;

    @ParameterizedTest
    @AutoSource
    public void sut_returns_social_user(GithubStaticUsers githubStaticUsers) {
        // given
        SocialOAuthAuthenticateCommand.ByAuthCode command = new SocialOAuthAuthenticateCommand.ByAuthCode(SocialOAuthProvider.GITHUB, githubStaticUsers.getCode());

        // when
        SocialOAuthUser actual = sut.authenticate(command);

        // then
        assertThat(actual.getProvider()).isEqualTo(SocialOAuthProvider.GITHUB);
        assertThat(actual.getEmail()).isEqualTo(githubStaticUsers.getEmail());
    }
}