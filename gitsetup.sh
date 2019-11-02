#/bin/sh

# Check if our github project is set up
response=$(curl --write-out %{http_code} --silent --output /dev/null https://github.com/npiper/game-of-mates)


# Check environment variables
if [[ -z $AWS_ACCESS_KEY_ID || -z $GITPW || -z $AWS_SECRET_KEY ]]; then	
  printf "Environment variables AWS_ACCESS_KEY_ID, AWS_SECRET_KEY and GITPW need to be set";
  exit 1;	
fi

printf "All env variables have values"

# Check status code
if [[ "$response" -ne 200 ]] ; then
  printf "game-of-mates repository has not been created .. trying to create using git creds"
  curl -u npiper:$GITPW https://api.github.com/user/repos -d '{"name":"game-of-mates"}'
  exit 1;
fi

# Check git & travis-ci are on path
# Check git is configured global for commits


# Initialise repository
git init
git add . && git commit -am "initial commit"
git remote add origin https://github.com/npiper/game-of-mates.git
git push --set-upstream origin master

travis login --user npiper --github-token $GITPW
travis sync
travis enable


# Encrypt travis variables into .travis.yml
travis encrypt AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID --add
travis encrypt AWS_SECRET_KEY=$AWS_SECRET_KEY --add
travis encrypt GIT_USER_NAME=npiper --add
travis encrypt GITPW=$GITPW --add

git add .travis.yml
git commit -m "Updated encrypted settings"
git push origin master


# Create 'develop' branch & push to repo
git branch develop
git checkout develop
git add .
git commit -m "adding a change from the develop branch"
git checkout master
git push origin develop      

# create gh-pages branch
git checkout --orphan gh-pages
git rm -rf .
touch README.md
git add README.md
git commit -m 'initial gh-pages commit'
git push origin gh-pages

git checkout develop
git merge master
git push --set-upstream origin develop
